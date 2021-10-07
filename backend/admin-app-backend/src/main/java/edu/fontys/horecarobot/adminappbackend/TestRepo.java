package edu.fontys.horecarobot.adminappbackend;

import edu.fontys.horecarobot.databaselibrary.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TestRepo extends JpaRepository<Product, UUID> {
}
