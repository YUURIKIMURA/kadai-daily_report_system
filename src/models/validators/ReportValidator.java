package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Report;

public class ReportValidator {
    public static List<String> validate(Report r) {
        List<String> errors = new ArrayList<String>();

/*
        String report_date_error = _validateReport_date(r.getReport_date());
        if(!report_date_error.equals("")) {
            errors.add(report_date_error);
        }
*/
        String title_error = _validateTitle(r.getTitle());
        if(!title_error.equals("")) {
            errors.add(title_error);
        }

        String attendancetime_error = _validateAttendanceTime(r.getAttendancetime());
        if(!attendancetime_error.equals("")) {
            errors.add(attendancetime_error);
        }

        String departuretime_error = _validateDepartureTime(r.getDeparturetime());
        if(!departuretime_error.equals("")) {
            errors.add(departuretime_error);
        }

        String content_error = _validateContent(r.getContent());
        if(!content_error.equals("")) {
            errors.add(content_error);
        }

        return errors;
    }

/*
    private static String _validateReport_date(String report_date) {
        if(report_date == null || report_date.equals("")) {
            return "日付が重複しています。";
            }

        return "";
    }

*/

    private static String _validateTitle(String title) {
        if(title == null || title.equals("")) {
            return "タイトルを入力してください。";
            }

        return "";
    }

    private static String _validateAttendanceTime(String attendancetime) {
        if(attendancetime == null || attendancetime.equals("")) {
            return "出勤時刻を入力してください。";
            }

        return "";
    }

    private static String _validateDepartureTime(String departuretime) {
        if(departuretime == null || departuretime.equals("")) {
            return "退勤時刻を入力してください。";
            }

        return "";
    }

    private static String _validateContent(String content) {
        if(content == null || content.equals("")) {
            return "内容を入力してください。";
            }

        return "";
    }
}