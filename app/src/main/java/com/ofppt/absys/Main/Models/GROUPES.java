package com.ofppt.absys.Main.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;


@Table(name = "GROUPES")
public class GROUPES extends Model {

    @Column(name = "CodeGroupe",unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String _CodeGroupe;

    @Column(name = "Filieres", onUpdate = Column.ForeignKeyAction.CASCADE)
    public FILIERES _filieres;

    @Column(name = "Annee")
    public int _Annee;

    public GROUPES() {
        super();
    }

    public GROUPES(String _CodeGroupe, FILIERES _filieres, int _Annee) {
        this._CodeGroupe = _CodeGroupe;
        this._filieres = _filieres;
        this._Annee = _Annee;
    }

    public static List<GROUPES> getAll() {
        return new Select()
                .from(GROUPES.class)
                .execute();
    }
    public static GROUPES getbycodeGroup(String g ) {
        return new Select()
                       .from(GROUPES.class)
                       .where("CodeGroupe = ?", g)
                       .executeSingle();
    }
}
