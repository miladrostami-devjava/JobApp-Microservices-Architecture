package com.jobapp.companyms.dao;

import com.jobapp.companyms.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity,Long > {


/*
    @Modifying
    @Query("""
update CompanyEntity c 
set c.ratingSum= + :deltaSum,
c.reviewCount= + :deltaCount,
c.averageRating= case 
when (c.reviewCount + :deltaCount) = 0 then 0 
else (c.ratingSum + :deltaSum)/(c.reviewCount + :deltaCount)
end 
where c.id = :companyId
""")
    int updateRatingAtomically(
            @Param("companyId") Long companyId,
            @Param("deltaSum") double deltaSum,
            @Param("deltaCount") int deltaCount
    );
*/


    @Transactional // برای کوئری‌های Update/Delete الزامی است
    @Modifying
    @Query("""
        update CompanyEntity c
        set c.ratingSum = c.ratingSum + :deltaSum,
            c.reviewCount = c.reviewCount + :deltaCount,
            c.averageRating = case
                when (c.reviewCount + :deltaCount) = 0 then 0.0
                else (c.ratingSum + :deltaSum) / (c.reviewCount + :deltaCount)
            end
        where c.id = :companyId
    """)
    int updateRatingAtomically(
            @Param("companyId") Long companyId,
            @Param("deltaSum") double deltaSum,
            @Param("deltaCount") int deltaCount
    );


}
