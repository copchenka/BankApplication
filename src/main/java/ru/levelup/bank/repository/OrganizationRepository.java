package ru.levelup.bank.repository;

import ru.levelup.bank.domain.Organization;

import java.util.List;

public interface OrganizationRepository {
    List<Organization> all();

    void update(Organization organization);

    Organization byVatin(String vatin);

    List<Organization> byName(String name);

    void assignCustomerToOrganization(Integer organizationId, Integer customerId);
}
