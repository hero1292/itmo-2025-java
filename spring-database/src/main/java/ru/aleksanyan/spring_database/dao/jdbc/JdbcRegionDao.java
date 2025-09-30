package ru.aleksanyan.spring_database.dao.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.aleksanyan.spring_database.dao.RegionDao;
import ru.aleksanyan.spring_database.domain.Region;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcRegionDao implements RegionDao {

    private final JdbcTemplate jdbc;

    private final RowMapper<Region> mapper = (rs, n) -> Region.builder()
            .id(rs.getLong("id"))
            .code(rs.getString("code"))
            .nameRu(rs.getString("name_ru"))
            .nameEn(rs.getString("name_en"))
            .build();

    @Override
    public Region create(Region region) {
        final String sql = "insert into regions(code, name_ru, name_en) values (?,?,?) returning id";
        Long id = jdbc.queryForObject(sql, Long.class,
                region.getCode(), region.getNameRu(), region.getNameEn());

        region.setId(id);

        return region;
    }

    @Override
    public Optional<Region> findById(Long id) {
        return jdbc.query("select * from regions where id = ?", mapper, id).stream().findFirst();
    }

    @Override
    public Optional<Region> findByCode(String code) {
        return jdbc.query("select * from regions where code = ?", mapper, code).stream().findFirst();
    }

    @Override
    public List<Region> findAll() {
        return jdbc.query("select * from regions order by name_ru", mapper);
    }

    @Override
    public Region update(Region r) {
        jdbc.update("update regions set code=?, name_ru=?, name_en=? where id=?",
                r.getCode(), r.getNameRu(), r.getNameEn(), r.getId());

        return r;
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbc.update("delete from regions where id=?", id) > 0;
    }
}
