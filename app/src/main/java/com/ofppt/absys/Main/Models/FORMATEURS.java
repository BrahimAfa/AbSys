package com.ofppt.absys.Main.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "FORMATEURS")
public class FORMATEURS extends Model {
    @Column(name = "Matricule",unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String _Matricule;

    @Column(name = "Nom")
    public String _Nom;

    @Column(name = "Prenom")
    public String _Prenom;

    @Column(name = "Crypto")
    public String _Crypto;

    public FORMATEURS() {super(); }

    public FORMATEURS(String _Matricule, String _Nom, String _Prenom, String _Crypto) {
        super();
        this._Matricule = _Matricule;
        this._Nom = _Nom;
        this._Prenom = _Prenom;
        this._Crypto = _Crypto;
    }
    public static List<FORMATEURS> getAll() {
        return new Select()
                .from(FORMATEURS.class)
                .orderBy("Nom ASC")
                .execute();
    }

    public static FORMATEURS getbyCrypte(String Crypte) {
        return new Select()
                       .from(FORMATEURS.class)
                       .where("Crypto = ?", Crypte)
                       .executeSingle();
    }
}
