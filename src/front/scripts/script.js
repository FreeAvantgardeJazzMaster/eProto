"use strict";

var RESTaddress = "http://localhost:8080";

function viewModel() {

    var self = this;
    self.students = ko.observableArray();
    self.courses = ko.observableArray();
    self.grades = ko.observableArray();

    self.getStudents = function(){
        $.ajax({
           type: "GET",
           dataType: "json",
           url: RESTaddress + "/students",
           success: function (data) {
               self.students($.map(data, function(student){
                   return new StudentViewModel(student);
               }));
           }
        });
    }

    self.getStudentGrades = function (index) {
        window.location = "#grades";
        $.ajax({
            type: "GET",
            dataType: "json",
            url: RESTaddress + "/students/" + index + "/grades",
            success: function (data) {
                self.grades($.map(data, function(grade){
                    return new GradeViewModel(grade);
                }));
            }
        });

    }

    self.getCourses = function(){
        $.ajax({
            type: "GET",
            dataType: "json",
            url: RESTaddress + "/courses",
            success: function (data) {
                self.courses($.map(data, function(course){
                    return new CourseViewModel(course);
                }));
            }
        });
    }

    self.getStudents();
    self.getCourses();
}

function StudentViewModel(student) {
    var self = this;
    self.index = ko.observable(student.index);
    self.firstName = ko.observable(student.firstName);
    self.lastName = ko.observable(student.lastName);
    self.birthDate = ko.observable(student.birthDate);
    self.grades = ko.observable(student.grades)
}

function CourseViewModel(course) {
    var self = this;
    self.name = ko.observable(course.name);
    self.lecturer = ko.observable(course.lecturer);
}

function GradeViewModel(grade) {
    var self = this;
    self.course = ko.observable(grade.course);
    self.value = ko.observable(grade.value);
    self.date = ko.observable(grade.date);
    self.id = ko.observable(grade.id);
}


$(document).ready(function () {
    ko.applyBindings(new viewModel());
});
