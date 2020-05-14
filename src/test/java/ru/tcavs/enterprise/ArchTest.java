package ru.tcavs.enterprise;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ru.tcavs.enterprise");

        noClasses()
            .that()
            .resideInAnyPackage("ru.tcavs.enterprise.service..")
            .or()
            .resideInAnyPackage("ru.tcavs.enterprise.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..ru.tcavs.enterprise.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
