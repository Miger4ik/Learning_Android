package com.example.student_accounting_journal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NbInformation extends AppCompatActivity {
    private Button backNb;
    private ListView groupsList;
    private List<Group> groupsL = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nb_information);

        // create rateBook
        Map<String, Float> rateBookForNastya = new HashMap<>();
        rateBookForNastya.put("Math", 5f);
        rateBookForNastya.put("Language", 4f);

        // create students
        Student nastya = new Student("Nastya", "Krupa", "Vitaliivna", 4.3f, rateBookForNastya);
        Student nastya2 = new Student("Nastya", "Krupa", "Vitaliivna", 4.3f, rateBookForNastya);

        // create list of student
        List<Student> studentsForP31 = new ArrayList<>();
        studentsForP31.add(nastya);
        studentsForP31.add(nastya2);

        // create group
        Group firstGroup = new Group("P-1", 4.3f, studentsForP31);
        Group firstGroup2 = new Group("P-2", 4.3f, studentsForP31);
        Group firstGroup3 = new Group("P-3", 4.3f, studentsForP31);
        Group firstGroup4 = new Group("P-4", 4.3f, studentsForP31);
        Group firstGroup5 = new Group("P-5", 4.3f, studentsForP31);
        Group firstGroup6 = new Group("P-6", 4.3f, studentsForP31);
        Group firstGroup7 = new Group("P-7", 4.3f, studentsForP31);
        Group firstGroup8 = new Group("P-8", 4.3f, studentsForP31);
        Group firstGroup9 = new Group("P-9", 4.3f, studentsForP31);
        Group firstGroup10 = new Group("P-10", 4.3f, studentsForP31);
        Group firstGroup11 = new Group("P-11", 4.3f, studentsForP31);
        Group firstGroup12 = new Group("P-12", 4.3f, studentsForP31);
        Group firstGroup13 = new Group("P-13", 4.3f, studentsForP31);

        //create group list

        groupsL.add(firstGroup);
        groupsL.add(firstGroup2);
        groupsL.add(firstGroup3);
        groupsL.add(firstGroup4);
        groupsL.add(firstGroup5);
        groupsL.add(firstGroup6);
        groupsL.add(firstGroup7);
        groupsL.add(firstGroup8);
        groupsL.add(firstGroup9);
        groupsL.add(firstGroup10);
        groupsL.add(firstGroup11);
        groupsL.add(firstGroup12);
        groupsL.add(firstGroup13);

        // create list Of Group names for display on screen
        final List<String> nameOfGroups = new ArrayList<>();
        for (Group temp : groupsL) {
            nameOfGroups.add(temp.getName());
        }

        backNb = findViewById(R.id.backNb);
        groupsList = findViewById(R.id.groups_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameOfGroups);

        groupsList.setAdapter(adapter);

        groupsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Group selectedGroup = groupsL.get(position);
                Log.d("Group -> ", selectedGroup.getName());

                // треба переробити шлях
                // треба переробити класи так щоб їх поля були тільки примітивами або масивами
//                Intent intentNbHandler = new Intent(NbInformation.this, MainActivity.class);
//                intentNbHandler.putExtra("groupName", selectedGroup.getName());
//                intentNbHandler.putExtra("groupRate", selectedGroup.getRate());
            }
        });

        backNb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBackNb = new Intent(NbInformation.this, MainActivity.class);
                startActivity(intentBackNb);
            }
        });
    }
}
