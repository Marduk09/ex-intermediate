package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Team;

/**
 * teamsテーブルを操作するレポジトリ.
 * 
 * @author takara.miyazaki
 * 
 */
@Repository
public class TeamRepository {

	private static final RowMapper<Team> TEAM_ROW_MAPPER = (rs, i) -> {
		Team team = new Team();
		team.setId(rs.getInt("id"));
		team.setLeagueName(rs.getString("league_name"));
		team.setTeamName(rs.getString("team_name"));
		team.setHeadquarters(rs.getString("headquarters"));
		team.setInauguration(rs.getDate("inauguration"));
		team.setHistory(rs.getString("history"));
		return team;
	};
	
	@Autowired
	NamedParameterJdbcTemplate template;
	
	/**
	 * 球団一覧情報を発足日順で取得する.
	 * 
	 * @return 球団一覧情報 球団が存在しない場合はサイズ0の一覧を返す
	 */
	public List<Team> findAll(){
		String sql = "SELECT id, league_name, team_name, headquarters, inauguration, history "
					+ "FROM teams ORDER BY inauguration;";
		
		List<Team> teamList = template.query(sql, TEAM_ROW_MAPPER);
		
		return teamList;
	}
	
	/**
	 * 主キーのIDから球団情報を取得する.
	 * 
	 * @param id 球団のID
	 * @return 球団情報
	 * @exception 球団が存在しない場合は例外を返す
	 */
	public Team load(Integer id) {
		String sql = "SELECT id, league_name, team_name, headquarters, inauguration, history "
					+ "FROM teams WHERE id=:id;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Team team = template.queryForObject(sql, param, TEAM_ROW_MAPPER);
		
		return team;
	}
}
