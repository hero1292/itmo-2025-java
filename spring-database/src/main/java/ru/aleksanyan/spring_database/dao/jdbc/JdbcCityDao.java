package ru.aleksanyan.spring_database.dao.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.aleksanyan.spring_database.dao.CityDao;
import ru.aleksanyan.spring_database.domain.City;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcCityDao implements CityDao {

    private final JdbcTemplate jdbc;

    private final RowMapper<City> mapper = (rs, n) -> City.builder()
            .id(rs.getLong("id"))
            .code(rs.getString("code"))
            .nameRu(rs.getString("name_ru"))
            .nameEn(rs.getString("name_en"))
            .population(rs.getLong("population"))
            .regionId(rs.getLong("region_id"))
            .build();

    @Override
    public City create(City city) {
        final String sql = "insert into cities(code, name_ru, name_en, population, region_id) " +
                "values (?,?,?,?,?) returning id";

        Long id = jdbc.queryForObject(sql, Long.class,
                city.getCode(),
                city.getNameRu(),
                city.getNameEn(),
                city.getPopulation(),
                city.getRegionId()
        );

        city.setId(id);

        return city;
    }

    @Override
    public Optional<City> findById(Long id) {
        return jdbc.query("select * from cities where id = ?", mapper, id).stream().findFirst();
    }

    @Override
    public Optional<City> findByCode(String code) {
        return jdbc.query("select * from cities where code = ?", mapper, code).stream().findFirst();
    }

    @Override
    public List<City> findAll() {
        return jdbc.query("""
                    select c.* from cities c
                    join regions r on r.id=c.region_id
                    order by r.name_ru, c.name_ru
                """, mapper);
    }

    @Override
    public List<City> findByRegionId(Long regionId) {
        return jdbc.query("select * from cities where region_id=? order by name_ru", mapper, regionId);
    }

    @Override
    public City update(City c) {
        jdbc.update("update cities set code=?, name_ru=?, name_en=?, population=?, region_id=? where id=?",
                c.getCode(), c.getNameRu(), c.getNameEn(), c.getPopulation(), c.getRegionId(), c.getId());

        return c;
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbc.update("delete from cities where id=?", id) > 0;
    }
}
