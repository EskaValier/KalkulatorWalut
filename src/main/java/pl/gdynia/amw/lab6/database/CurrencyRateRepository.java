package pl.gdynia.amw.lab6.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called CurrencyRateRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Integer> {

    @Override
    List<CurrencyRate> findAll();

    List<CurrencyRate> findTop5ByOrderByRateDesc(); //query by method name

    //query by @Query
    @Query(value="select cr from CurrencyRate cr where cr.date = ?1 and cr.currencyTo = ?2")
    List<CurrencyRate> findByDateAndCurrTo(LocalDate date, String to);

    //query by @Query
    @Query(value="select cr from CurrencyRate cr where cr.date = ?1")
    List<CurrencyRate> findByDate(LocalDate date);
}
