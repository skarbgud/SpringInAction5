package tacos.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;

/*
 * 스테레오타입 애노테이션 : 스프링에서 주로 사용하는 역할 그룹을 나타내는 애노테이션
 */
@Repository // @Controller와 @Component 외에 스프링이 정의하는 몇 안되는 스테레오타입 애노테이션이다.
public class JdbcIngredientRepository implements IngredientRepository{

	private JdbcTemplate jdbc;
	
	@Autowired
	public JdbcIngredientRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Iterable<Ingredient> findAll() {
		return jdbc.query("select id, name, type from Ingredient", 
				this::mapRowToIngredient);
	}

	@Override
	public Ingredient findById(String id) {
		return jdbc.queryForObject("select id, name, type from Ingredient where id = ?",
				this::mapRowToIngredient, id);
	}
	
	private Ingredient mapRowToIngredient(ResultSet rs, int row) throws SQLException
	{
		return new Ingredient(
				rs.getString("id"),
				rs.getString("name"),
				Ingredient.Type.valueOf(rs.getString("type")));
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		jdbc.update("insert into Ingredient (id, name, type) values (?, ?, ?)",
				ingredient.getId(),
				ingredient.getName(),
				ingredient.getType().toString());
		return ingredient;
	}
}
