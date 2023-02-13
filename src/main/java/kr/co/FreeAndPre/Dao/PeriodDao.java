package kr.co.FreeAndPre.Dao;


import kr.co.FreeAndPre.Model.GetPeriodRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class PeriodDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetPeriodRes getAbuseByIdx(int periodId) {
        String getPeriodByIdQuery = "SELECT start_date, end_date FROM Period WHERE period_id = ?;";
        int getPeriodByIdParams = periodId;
        return this.jdbcTemplate.queryForObject(getPeriodByIdQuery,
                (rs, rowNum) -> new GetPeriodRes (
                        rs.getString("start_date"),
                        rs.getString("end_date")
                ), getPeriodByIdParams
        );
    }
}
