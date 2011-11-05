package common.money;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;

import common.repository.ImmutableValueUserType;

/**
 * A Hibernate user type for the Percentage type. This class enables Hibernate to map a Percentage object to and from a
 * double column type in a database.
 * @see Percentage
 */
public class PercentageUserType extends ImmutableValueUserType {

	public Class returnedClass() {
		return Percentage.class;
	}

	public int[] sqlTypes() {
		return new int[] { Types.NUMERIC };
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		BigDecimal value = rs.getBigDecimal(names[0]);
		if (value == null) {
			return null;
		} else {
			return new Percentage(value);
		}
	}

	public void nullSafeSet(PreparedStatement ps, Object value, int index) throws HibernateException, SQLException {
		if (value == null) {
			ps.setNull(index, Types.NUMERIC);
		} else {
			Percentage p = (Percentage) value;
			ps.setBigDecimal(index, p.asBigDecimal());
		}
	}
}