package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.samples.petclinic.model.Owner;

//@RepositoryRestResource(collectionResourceRel = "owners", path = "owners")
public interface OwnerRepository extends PagingAndSortingRepository<Owner, Integer> {

//	List<Owner> findByLastName(@Param("name") String name);
	
    @Query("SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName LIKE :lastName%")
    public Collection<Owner> findByLastName(@Param("lastName") String lastName);

    @Query("SELECT owner FROM Owner owner left join fetch owner.pets WHERE owner.id =:id")
    public Owner findById(@Param("id") int id);

}
