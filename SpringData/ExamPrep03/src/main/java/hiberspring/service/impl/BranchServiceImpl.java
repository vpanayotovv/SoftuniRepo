package hiberspring.service.impl;

import hiberspring.service.BranchService;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class BranchServiceImpl implements BranchService {
    @Override
    public Boolean branchesAreImported() {
        return false;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return null;
    }

    @Override
    public String importBranches(String branchesFileContent) throws FileNotFoundException {
        return null;
    }
}
