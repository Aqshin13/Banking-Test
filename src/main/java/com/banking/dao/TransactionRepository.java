package com.banking.dao;

import com.banking.entity.Transaction;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {



    @Query("select t from Transaction t where" +
            "  t.type=com.banking.entity.Transaction.TransactionType.PURCHASE and t.isRefund=false and  t.id=:id")
    Optional<Transaction> getTransaction(Long id);



    @Query("select t from Transaction t where" +
            "  t.receiver=:id or t.sender=:id ")
    List<Transaction> findCustomerTransaction(@Param("id") Long id);
}
