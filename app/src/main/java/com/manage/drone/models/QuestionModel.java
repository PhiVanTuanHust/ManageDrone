package com.manage.drone.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phí Văn Tuấn on 5/11/2018.
 */

public class QuestionModel extends BaseItemModel {
    public QuestionModel(String title, String answer) {
        super(title, false);
        this.answer = answer;
    }

    private List<String> lstAnswer;

    public QuestionModel(String title, List<String> lstAnswer) {
        super(title,false);
        this.lstAnswer = lstAnswer;
    }

    private String answer;

    public QuestionModel() {
    }

    @Override
    public int getType() {
        return 0;
    }

    public List<QuestionModel> getQuestionSuggest() {
        List<QuestionModel> lst = new ArrayList<>();
        lst.add(new QuestionModel("Làm thế nào để kết nối thiết bị với cầm tay của kiểm lâm với drone?", getAnswers(lst.size())));
        lst.add(new QuestionModel("Làm thế nào để chọn lịch trình bay và chế độ bay cho drone", getAnswers(lst.size())));
        lst.add(new QuestionModel("Làm thế nào để đổi sang chế độ bay tự lái", getAnswers(lst.size())));
        lst.add(new QuestionModel("Làm thế nào để tìm kiếm ảnh chụp khu vực tại 1 thời điểm ", getAnswers(lst.size())));
        lst.add(new QuestionModel("Làm thế nào để phân tích so sánh hai ảnh ", getAnswers(lst.size())));
        lst.add(new QuestionModel("Làm thế nào để cập nhật ảnh mới lên hệ thống ", getAnswers(lst.size())));
        return lst;
    }

    private List<String> getAnswers(int position) {
        List<String> lstAnswers = new ArrayList<>();
        switch (position) {
            case 0:
                lstAnswers.add("Đăng nhập tài khoản người dùng");
                lstAnswers.add("Bật wifi của drone sẽ kết nối");
                lstAnswers.add("Chọn chức năng kết nối với drone trên menu");
                lstAnswers.add("Chọn wifi của drone cần kết nối. Và kiểm tra xem thông báo kết nối thành công hay thất bại");
                lstAnswers.add("Nếu thất bại hãy thực hiện lại bước chọn wifi");
                break;
            case 1:
                lstAnswers.add("Chọn mục cấu hình bay trên menu sau khi đăng nhập và kết nối drone thành công");
                lstAnswers.add("Chọn kiểu bay: tự động hay tự lái");
                lstAnswers.add("Nếu kiểu bay là tự động, hãy chọn các điểm trên giao diện bản đồ hoặc nhập tọa độ các điểm cần bay qua của drone");
                lstAnswers.add("Chọn hoàn thành và giao diện sẽ hiển thị lại thông tin lộ trình trên giao diện cho người dùng");
                lstAnswers.add("Chọn khởi hành khi đã hoàn thành kiểm tra các thông đã nhập");
                break;
            case 2:
                lstAnswers.add("Khi drone đang trong trạng thái bay, chọn nút chuyển chế độ bay tự lái trên giao diện");
                lstAnswers.add("Màn hình hiển thị thông báo xác nhận yêu cầu chuyển chế độ bay");
                lstAnswers.add("Chọn xác nhận và trên màn hình hiển thị giao diện điểu khiển tự lái với các nút tương ứng các chuyển động bay lên, hạ xuống, sang trái sang phải kèm với màn hình hiển thị vị trí của drone trên bản đồ");
                lstAnswers.add("Nếu muốn quay lại chế độ tự lái, chọn nút chuyển chế độ bay tự động, thiết lập lại tọa độ bay");
                break;
            case 3:
                lstAnswers.add("Đăng nhập với tài khoản người quản lý hệ thống");
                lstAnswers.add("Chọn chức năng tìm kiếm kho ảnh hiển thị lên một giao diện chức các thư mục ảnh về khu vực");
                lstAnswers.add("Chọn thư mục khu vực cần tìm kiếm");
                lstAnswers.add("Trong thư mục chứa các thư mục nhỏ ghi rõ thời gian từng địa điểm và lựa. Click vào thư mục đó và ấn chọn. Màn hình sẽ hiển thị ảnh trên toàn bộ khu vực vào thời điểm đó");

                break;
            case 4:
                lstAnswers.add("Đăng nhập với tài khoản người quản lý hệ thống");
                lstAnswers.add("Chọn nút so sánh hai ảnh trên menu và hiển thị giao diện so sánh với màn hình được chia thành hai nửa");
                lstAnswers.add("Chọn khu vực cần so sánh.  Sau đó ở mỗi nửa chọn file thư mục chứa ảnh tại thời điểm muốn so sánh");
                lstAnswers.add("Chọn nút phân tích. Màn hình sẽ hiện thị thông báo các điểm khác nhau trên ảnh kèm thông báo");
                break;
            case 5:
                lstAnswers.add("Đăng nhập với tài khoản người quản lý hệ thống");
                lstAnswers.add("Cắm thẻ nhớ của drone vào máy chủ của hệ thống");
                lstAnswers.add("Chọn nút cập nhật ảnh mới");
                lstAnswers.add("Nhập tên khu vực khảo sát của drone và chọn hoàn thành");

                break;
        }

        return lstAnswers;
    }

    public List<String> getLstAnswer() {
        return this.lstAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
