package softuni.jsonexercise.domain.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public class CustomerExportDto {

    @Expose
    private Long id;

    @Expose
    private String name;

    @Expose
    private String birthDate;

    @Expose
    private boolean isYoungDriver;

    @Expose
    private List<SaleExposeDto> sales;
}
