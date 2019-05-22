package Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Stagiare")
public class STAGIAIRES extends Model {
    @Column(name = "CEF", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String _CEF;

    @Column(name = "GROUPE", onUpdate = Column.ForeignKeyAction.CASCADE)
    public GROUPES _groupes;

    @Column(name = "Nom")
    public String _Nom;

    @Column(name = "Prenom")
    public String _Prenom;

    @Column(name = "Absence_Cumulee")
    public Double _Absence_Cumulee;


    public STAGIAIRES() {
        super();
    }


    public static List<STAGIAIRES> getAll() {
        return new Select()
                .from(STAGIAIRES.class)
                .orderBy("Nom ASC")
                .execute();
    }
}
