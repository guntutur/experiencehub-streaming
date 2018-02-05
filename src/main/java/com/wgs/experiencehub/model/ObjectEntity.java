//package com.wgs.experiencehub.model;
//
//import com.arangodb.entity.DocumentField;
//
//import java.util.LinkedHashMap;
//
///**
// * Created by zer0 , The Maverick Hunter
// * On 21/03/17 - 1:15
// */
//public class ObjectEntity {
//
//    /* we need to set the key so we can manipulate it */
//    @DocumentField(DocumentField.Type.KEY)
//    private String _key;
//    private LinkedHashMap<String, LinkedHashMap<String, String>> an;
//    private String obj_id;
//    private String case_id;
//    private String img;
//    private String label;
//    private String type;
//
//    public ObjectEntity(
//            String _key,
//            LinkedHashMap<String, LinkedHashMap<String, String>> an,
//            String obj_id,
//            String case_id,
//            String img,
//            String label,
//            String type
//    ) {
//        super();
//        this._key = _key;
//        this.an = an;
//        this.obj_id = obj_id;
//        this.case_id = case_id;
//        this.img = img;
//        this.label = label;
//        this.type = type;
//
//    }
//
//    public String getObj_id() {
//        return obj_id;
//    }
//
//    public void setObj_id(String obj_id) {
//        this.obj_id = obj_id;
//    }
//    public String get_key() {
//        return _key;
//    }
//
//    public void set_key(String _key) {
//        this._key = _key;
//    }
//
//    public LinkedHashMap<String, LinkedHashMap<String, String>> getAn() {
//        return an;
//    }
//
//    public void setAn(LinkedHashMap<String, LinkedHashMap<String, String>> an) {
//        this.an = an;
//    }
//
//    public String getCase_id() {
//        return case_id;
//    }
//
//    public void setCase_id(String case_id) {
//        this.case_id = case_id;
//    }
//
//    public String getImg() {
//        return img;
//    }
//
//    public void setImg(String img) {
//        this.img = img;
//    }
//
//    public String getLabel() {
//        return label;
//    }
//
//    public void setLabel(String label) {
//        this.label = label;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//}
