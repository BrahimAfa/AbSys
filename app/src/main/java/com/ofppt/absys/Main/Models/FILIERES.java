package com.ofppt.absys.Main.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "FILIERES" )
public class FILIERES extends Model {

    @Column(name = "CodeFiliere",unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String _CodeFiliere;

    @Column(name = "Filiere")
    public String _Filiere;

    public FILIERES() {
        super();
    }

    public FILIERES(String _CodeFiliere, String _Filiere) {
        this._CodeFiliere = _CodeFiliere;
        this._Filiere = _Filiere;
    }

    public static List<FILIERES> getAll() {
        return new Select()
                .from(FILIERES.class)
                .execute();
    }
}
