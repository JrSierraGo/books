package go.jrsierra.jpa.helper;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SqlTypes;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;

import java.io.Serializable;
import java.sql.*;
import java.util.Objects;

public class TsVectorType implements UserType<PGobject> {


    @Override
    public int getSqlType() {
        return SqlTypes.OTHER;
    }

    @Override
    public Class<PGobject> returnedClass() {
        return PGobject.class;
    }

    @Override
    public boolean equals(PGobject x, PGobject y) {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(PGobject x) {
        return x != null ? x.hashCode() : 0;
    }

    @Override
    public PGobject nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner ){
        try {
            if (rs.getObject(position) == null) {
                return null;
            }
            PGobject pGobject = new PGobject();
            pGobject.setType("tsvector");
            pGobject.setValue(rs.getString(position));
            return pGobject;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, PGobject value, int index, SharedSessionContractImplementor session) {
        try {
            if (value == null) {
                st.setNull(index, Types.OTHER);
            } else {
                ResultSet resultSet = st.getConnection().prepareStatement("SELECT " + value.getValue()).executeQuery();
                String textVectorized = resultSet.next() ? resultSet.getString(1) : value.getValue();
                st.setObject(index, textVectorized, SqlTypes.OTHER);
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public PGobject deepCopy(PGobject value) {
        if (value == null) {
            return null;
        }
        PGobject copy = new PGobject();
        try {
            copy.setType(value.getType());
            copy.setValue(value.getValue());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(PGobject value) {
        return deepCopy(value);
    }

    @Override
    public PGobject assemble(Serializable cached, Object owner) {
        return deepCopy((PGobject) cached);
    }

    @Override
    public PGobject replace(PGobject detached, PGobject managed, Object owner) {
        return deepCopy(detached);
    }

}
