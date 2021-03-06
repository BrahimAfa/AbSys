package com.ofppt.absys.Main.Models;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

@Table(name = "GroupEnrg" )
public class GroupEnrg extends Model{

    @Column(name = "GroupAb",onUpdate = Column.ForeignKeyAction.CASCADE)
    public GROUPES _Group;

    @Column( name = "DateAbsence")
    public Date _DateAbsence;

    @Column(name = "Seance")
    public String _Seance;

    @Column(name = "Formateur",onUpdate = Column.ForeignKeyAction.CASCADE)
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
