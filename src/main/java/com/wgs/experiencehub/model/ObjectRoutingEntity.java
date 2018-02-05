//package com.wgs.experiencehub.model;
//
//import com.arangodb.entity.DocumentField;
//
//import java.util.LinkedHashMap;
//
///**
// * Created by zer0 , The Maverick Hunter
// * On 20/03/17 - 23:58
// */
//public class ObjectRoutingEntity {
//
//    @DocumentField(DocumentField.Type.FROM)
//    private String _from;
//    @DocumentField(DocumentField.Type.TO)
//    private String _to;
//    private LinkedHashMap<String, LinkedHashMap<String, String>> ae;
//    private String case_id;
//    private String message_id;
//    private String source;
//    private String target;
//    private String type;
//
//    public ObjectRoutingEntity(
//            String _from,
//            String _to,
//            LinkedHashMap<String, LinkedHashMap<String, String>> ae,
//            String case_id,
//            String message_id,
//            String source,
//            String target,
//            String type
//    ) {
//        super();
//        this._from = _from;
//        this._to = _to;
//        this.ae = ae;
//        this.case_id = case_id;
//        this.message_id = message_id;
//        this.source = source;
//        this.target = target;
//        this.type = type;
//
//    }
//
//    public LinkedHashMap<String, LinkedHashMap<String, String>> getAe() {
//        return ae;
//    }
//    public void setAe(LinkedHashMap<String, LinkedHashMap<String, String>> ae) {
//        this.ae = ae;
//    }
//    public String getCase_id() {
//        return case_id;
//    }
//    public void setCase_id(String case_id) {
//        this.case_id = case_id;
//    }
//    public String getMessage_id() {
//        return message_id;
//    }
//    public void setMessage_id(String message_id) {
//        this.message_id = message_id;
//    }
//    public String getSource() {
//        return source;
//    }
//    public void setSource(String source) {
//        this.source = source;
//    }
//    public String getTarget() {
//        return target;
//    }
//    public void setTarget(String target) {
//        this.target = target;
//    }
//    public String get_from() {
//        return _from;
//    }
//    public void set_from(String _from) {
//        this._from = _from;
//    }
//    public String get_to() {
//        return _to;
//    }
//    public void set_to(String _to) {
//        this._to = _to;
//    }
//    public String getType() {
//        return type;
//    }
//    public void setType(String type) {
//        this.type = type;
//    }
//
//
//}
