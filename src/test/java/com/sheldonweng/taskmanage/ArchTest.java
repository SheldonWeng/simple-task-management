package com.sheldonweng.taskmanage;

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
            .importPackages("com.sheldonweng.taskmanage");

        noClasses()
            .that()
            .resideInAnyPackage("com.sheldonweng.taskmanage.service..")
            .or()
            .resideInAnyPackage("com.sheldonweng.taskmanage.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.sheldonweng.taskmanage.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
