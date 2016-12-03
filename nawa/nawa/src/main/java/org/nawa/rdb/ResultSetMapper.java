package org.nawa.rdb;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ResultSetMapper<R> {
	private R result;

	public R getResult() {
		return result;
	}

	public void setResult(R result) {
		this.result = result;
	}

	public abstract void map(ResultSet rs) throws SQLException;
} // interface