package com.ofppt.absys.Main.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.sql.Date;
import java.util.List;

@Table(name = "GroupEnrg" ,id = "IdEnreg")
public class GroupEnrg extends Model{

    @Column(name = "Group")
    public GROUPES _Group;

    @Column( name = "DateAbsence")
    public Date _DateAbsence;

    @Column(name = "Seance")
    public String _Seance;

    @Column(name = "Formateur")
    public FORMATEURS _Formateur;

    public GroupEnrg(GROUPES _Group, Date _DateAbsence, String _Seance, FORMATEURS _Formateur) {
        this._Group = _Group;
        this._DateAbsence = _DateAbsence;
        this._Seance = _Seance;
        this._Formateur = _Formateur;
    }

    public GroupEnrg() {
        super();
    }
    public static List<GroupEnrg> getAll() {
        return new Select()
                .from(GroupEnrg.class)
                .execute();
    }
}
