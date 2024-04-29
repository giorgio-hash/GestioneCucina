package com.example.gestionecucina.Infrastructure.Repository;

import com.example.gestionecucina.Domain.DataPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Sql({"/db/schema.sql","/db/data-test.sql"})
class JPADBAdapterTests {


    private final DataPort dataPort;

    @Autowired
    public JPADBAdapterTests(DataPort dataPort) {
        this.dataPort = dataPort;
    }

    @Test
    public void fetchExistingIngredienti(){

        List<String> res = dataPort.getIdIngredienti();
        assertThat(res.size()).isGreaterThan(0);
        for(String s : res) assertThat(s).isUpperCase();

    }
}
