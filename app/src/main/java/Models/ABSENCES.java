package Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

@Table(name = "ABSENCES")
public class ABSENCES extends Model {

    @Column(name = "idAbsence",unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public Long _idAbsence;

    @Column(name = "Stagiere",onUpdate = Column.ForeignKeyAction.CASCADE)
    public STAGIAIRES _Stagiere;

    @Column(name = "Matricule",onUpdate = Column.ForeignKeyAction.CASCADE)
    public FORMATEURS _Formateurs;

    @Column(name = "DateAbsence")
    public Date _DateAbsence;

    @Column(name = "Seance")
    public String _Seance;

    public ABSENCES() {
        super();
    }
    public static List<ABSENCES> getAll() {
        return new Select()
                .from(ABSENCES.class)
                .execute();
    }
}
