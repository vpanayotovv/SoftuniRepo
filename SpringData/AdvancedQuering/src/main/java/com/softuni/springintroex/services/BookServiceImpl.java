package com.softuni.springintroex.services;

import com.softuni.springintroex.constants.GlobalConstants;
import com.softuni.springintroex.entities.*;
import com.softuni.springintroex.repositories.BookRepository;
import com.softuni.springintroex.utils.CustomFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;
    private final BookRepository bookRepository;
    private final CustomFileReader customFileReader;
    private final CategoryService categoryService;

    @Autowired
    public BookServiceImpl(AuthorService authorService, BookRepository bookRepository, CustomFileReader customFileReader, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookRepository = bookRepository;
        this.customFileReader = customFileReader;
        this.categoryService = categoryService;
    }

    @Transactional
    @Override
    public void seedBooks() throws FileNotFoundException {
        if (this.bookRepository.count() != 0) {
            return;
        }

        List<String> fileInput = customFileReader.read(GlobalConstants.BOOKS_FILE_PATH);

        fileInput.forEach(r -> {
            String[] params = r.split("\\s+");

            Author author = getRandomAuthor();

            EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

            LocalDate releaseDate = LocalDate.parse(params[1], formatter);

            int copies = Integer.parseInt(params[2]);

            BigDecimal price = new BigDecimal(params[3]);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(params[4])];

            String title = getTitle(params);

            Set<Category> categories = getRandomCategories();

            Book book = new Book(author, editionType, releaseDate, copies, price, ageRestriction, title, categories);

            this.bookRepository.saveAndFlush(book);

        });
    }

    @Override
    public List<Book> getBooksByAgeRestriction(String ageRes) {
        return this.bookRepository.findAllByAgeRestriction(AgeRestriction.valueOf(ageRes.toUpperCase()));
    }

    @Override
    public List<Book> getGoldBooksWithLessThen5000Copies() {
        return this.bookRepository.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000);
    }

    @Override
    public List<Book> getBooksInPriceRange() {
        return this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40));
    }

    @Override
    public List<Book> getBooksNotReleasedInGivenYear(String year) {

        return this.bookRepository.findBookNotReleasedInYear(year);
    }

    @Override
    public List<Book> getBooksReleasedBeforeDate(String date) {
        String[] split = date.split("-");
        LocalDate localDate = LocalDate.of(Integer.parseInt(split[2]),Integer.parseInt(split[1]),Integer.parseInt(split[0]));

        return this.bookRepository.findAllByReleaseDateBefore(localDate);
    }

    @Override
    public List<Book> getBooksTitleContains(String text) {
        return this.bookRepository.findAllByTitleContaining(text);
    }

    @Override
    public List<Book> getBooksAuthorsLastNameStartWith(String text) {
        return this.bookRepository.findAllByAuthorLastNameStartingWith(text);
    }

    @Override
    public int getCountOfBooks(int length) {
        return this.bookRepository.getCountBooksWithTitleIsLongerThen(length);
    }

    @Override
    public List<String> getBooksByTitleName(String title) {
        List<String> result = new ArrayList<>();
        for (Book book : this.bookRepository.findByTitle(title)) {
            result.add(book.getTitle());
            result.add(book.getEditionType().toString());
            result.add(book.getAgeRestriction().toString());
            result.add(book.getPrice().toString());
        }
        return result;
    }

    private Set<Category> getRandomCategories() {

        Set<Category> result = new HashSet<>();

        int randomId = ThreadLocalRandom.current().nextInt(1, 4);

        for (int i = 1; i <= randomId; i++) {
            int randomCategory = ThreadLocalRandom.current().nextInt(1, this.categoryService.getCategoryCount() + 1);
            result.add(this.categoryService.getCategoryById(randomCategory));
        }
        return result;

    }

    private String getTitle(String[] params) {
        StringBuilder result = new StringBuilder();

        for (int i = 5; i < params.length; i++) {
            result.append(params[i]).append(" ");
        }
        return result.toString().trim();
    }

    private Author getRandomAuthor() {

        int randomId = ThreadLocalRandom.current().nextInt(1, this.authorService.geAllAuthorsCount() + 1);
        return this.authorService.findAuthorById(randomId);

    }
}
