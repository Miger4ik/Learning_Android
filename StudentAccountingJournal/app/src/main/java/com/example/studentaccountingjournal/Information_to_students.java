package com.example.studentaccountingjournal;

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

public class Information_to_students extends AppCompatActivity {

    private Button backButton;
    private ListView groupsListView;

    private ArrayList<Group> groups = new ArrayList<>();
    private ArrayList<String> arrayGroupNames = new ArrayList<>();
    private ArrayList<Student> students_A11 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_to_students);

        backButton = findViewById(R.id.back_button);
        groupsListView = findViewById(R.id.groups);

        students_A11.add(new Student("Nastya", "5.0"));
        students_A11.add(new Student("Bodya", "2.0"));
        students_A11.add(new Student("Rostik", "3.0"));
        students_A11.add(new Student("Vitya", "4.0"));

        groups.add(new Group("А-11", students_A11));
        groups.add(new Group("А-21", students_A11));
        groups.add(new Group("А-31", students_A11));
        groups.add(new Group("А-41", students_A11));
        groups.add(new Group("А-42", students_A11));

        for (Group group : groups){
            arrayGroupNames.add(group.getNameGroup());
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Information_to_students.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arrayGroupNames);

        groupsListView.setAdapter(adapter);

        groupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("LOG_TAG", groups.get(position).nameGroup);
            }
        });
    }

    class Group {
        private String nameGroup;
        private ArrayList<String> list = new ArrayList<>();
        private ArrayList<String> rate = new ArrayList<>();
        private ArrayList<Student> students = new ArrayList<>();

        Group(){}

        Group(String nameGroup, ArrayList<Student> students) {
            this.nameGroup = nameGroup;
            this.students = students;

            for(Student student : students){
                list.add(student.getName());
                rate.add(student.getRate());
            }
        }

        public String getNameGroup() {
            return nameGroup;
        }

        public ArrayList<String> getList() {
            return list;
        }

        public ArrayList<String> getRate() {
            return rate;
        }

        public ArrayList<Student> getStudents() {
            return students;
        }

        public void setNameGroup(String nameGroup) {
            this.nameGroup = nameGroup;
        }

        public void setList(ArrayList<String> list) {
            this.list = list;
        }

        public void setRate(ArrayList<String> rate) {
            this.rate = rate;
        }

        public void setStudents(ArrayList<Student> students) {
            this.students = students;
        }

        @Override
        public String toString() {
            return "Group{" + "nameGroup=" + nameGroup +'}';
        }
    }
    class Student {
        private String name;
        private String rate;

        public Student() {}

        public Student(String name, String rate) {
            this.name = name;
            this.rate = rate;
        }

        public String getName() {
            return name;
        }

        public String getRate() {
            return rate;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }
    }
}
