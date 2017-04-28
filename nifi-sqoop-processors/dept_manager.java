// ORM class for table 'dept_manager'
// WARNING: This class is AUTO-GENERATED. Modify at your own risk.
//
// Debug information:
// Generated date: Thu Apr 27 16:15:18 EDT 2017
// For connector: org.apache.sqoop.manager.GenericJdbcManager
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;
import com.cloudera.sqoop.lib.JdbcWritableBridge;
import com.cloudera.sqoop.lib.DelimiterSet;
import com.cloudera.sqoop.lib.FieldFormatter;
import com.cloudera.sqoop.lib.RecordParser;
import com.cloudera.sqoop.lib.BooleanParser;
import com.cloudera.sqoop.lib.BlobRef;
import com.cloudera.sqoop.lib.ClobRef;
import com.cloudera.sqoop.lib.LargeObjectLoader;
import com.cloudera.sqoop.lib.SqoopRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class dept_manager extends SqoopRecord  implements DBWritable, Writable {
  private final int PROTOCOL_VERSION = 3;
  public int getClassFormatVersion() { return PROTOCOL_VERSION; }
  protected ResultSet __cur_result_set;
  private Integer emp_no;
  public Integer get_emp_no() {
    return emp_no;
  }
  public void set_emp_no(Integer emp_no) {
    this.emp_no = emp_no;
  }
  public dept_manager with_emp_no(Integer emp_no) {
    this.emp_no = emp_no;
    return this;
  }
  private String dept_no;
  public String get_dept_no() {
    return dept_no;
  }
  public void set_dept_no(String dept_no) {
    this.dept_no = dept_no;
  }
  public dept_manager with_dept_no(String dept_no) {
    this.dept_no = dept_no;
    return this;
  }
  private java.sql.Date from_date;
  public java.sql.Date get_from_date() {
    return from_date;
  }
  public void set_from_date(java.sql.Date from_date) {
    this.from_date = from_date;
  }
  public dept_manager with_from_date(java.sql.Date from_date) {
    this.from_date = from_date;
    return this;
  }
  private java.sql.Date to_date;
  public java.sql.Date get_to_date() {
    return to_date;
  }
  public void set_to_date(java.sql.Date to_date) {
    this.to_date = to_date;
  }
  public dept_manager with_to_date(java.sql.Date to_date) {
    this.to_date = to_date;
    return this;
  }
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof dept_manager)) {
      return false;
    }
    dept_manager that = (dept_manager) o;
    boolean equal = true;
    equal = equal && (this.emp_no == null ? that.emp_no == null : this.emp_no.equals(that.emp_no));
    equal = equal && (this.dept_no == null ? that.dept_no == null : this.dept_no.equals(that.dept_no));
    equal = equal && (this.from_date == null ? that.from_date == null : this.from_date.equals(that.from_date));
    equal = equal && (this.to_date == null ? that.to_date == null : this.to_date.equals(that.to_date));
    return equal;
  }
  public boolean equals0(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof dept_manager)) {
      return false;
    }
    dept_manager that = (dept_manager) o;
    boolean equal = true;
    equal = equal && (this.emp_no == null ? that.emp_no == null : this.emp_no.equals(that.emp_no));
    equal = equal && (this.dept_no == null ? that.dept_no == null : this.dept_no.equals(that.dept_no));
    equal = equal && (this.from_date == null ? that.from_date == null : this.from_date.equals(that.from_date));
    equal = equal && (this.to_date == null ? that.to_date == null : this.to_date.equals(that.to_date));
    return equal;
  }
  public void readFields(ResultSet __dbResults) throws SQLException {
    this.__cur_result_set = __dbResults;
    this.emp_no = JdbcWritableBridge.readInteger(1, __dbResults);
    this.dept_no = JdbcWritableBridge.readString(2, __dbResults);
    this.from_date = JdbcWritableBridge.readDate(3, __dbResults);
    this.to_date = JdbcWritableBridge.readDate(4, __dbResults);
  }
  public void readFields0(ResultSet __dbResults) throws SQLException {
    this.emp_no = JdbcWritableBridge.readInteger(1, __dbResults);
    this.dept_no = JdbcWritableBridge.readString(2, __dbResults);
    this.from_date = JdbcWritableBridge.readDate(3, __dbResults);
    this.to_date = JdbcWritableBridge.readDate(4, __dbResults);
  }
  public void loadLargeObjects(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void loadLargeObjects0(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void write(PreparedStatement __dbStmt) throws SQLException {
    write(__dbStmt, 0);
  }

  public int write(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeInteger(emp_no, 1 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeString(dept_no, 2 + __off, 1, __dbStmt);
    JdbcWritableBridge.writeDate(from_date, 3 + __off, 91, __dbStmt);
    JdbcWritableBridge.writeDate(to_date, 4 + __off, 91, __dbStmt);
    return 4;
  }
  public void write0(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeInteger(emp_no, 1 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeString(dept_no, 2 + __off, 1, __dbStmt);
    JdbcWritableBridge.writeDate(from_date, 3 + __off, 91, __dbStmt);
    JdbcWritableBridge.writeDate(to_date, 4 + __off, 91, __dbStmt);
  }
  public void readFields(DataInput __dataIn) throws IOException {
this.readFields0(__dataIn);  }
  public void readFields0(DataInput __dataIn) throws IOException {
    if (__dataIn.readBoolean()) { 
        this.emp_no = null;
    } else {
    this.emp_no = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.dept_no = null;
    } else {
    this.dept_no = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.from_date = null;
    } else {
    this.from_date = new Date(__dataIn.readLong());
    }
    if (__dataIn.readBoolean()) { 
        this.to_date = null;
    } else {
    this.to_date = new Date(__dataIn.readLong());
    }
  }
  public void write(DataOutput __dataOut) throws IOException {
    if (null == this.emp_no) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.emp_no);
    }
    if (null == this.dept_no) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, dept_no);
    }
    if (null == this.from_date) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeLong(this.from_date.getTime());
    }
    if (null == this.to_date) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeLong(this.to_date.getTime());
    }
  }
  public void write0(DataOutput __dataOut) throws IOException {
    if (null == this.emp_no) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.emp_no);
    }
    if (null == this.dept_no) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, dept_no);
    }
    if (null == this.from_date) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeLong(this.from_date.getTime());
    }
    if (null == this.to_date) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeLong(this.to_date.getTime());
    }
  }
  private static final DelimiterSet __outputDelimiters = new DelimiterSet((char) 44, (char) 10, (char) 0, (char) 0, false);
  public String toString() {
    return toString(__outputDelimiters, true);
  }
  public String toString(DelimiterSet delimiters) {
    return toString(delimiters, true);
  }
  public String toString(boolean useRecordDelim) {
    return toString(__outputDelimiters, useRecordDelim);
  }
  public String toString(DelimiterSet delimiters, boolean useRecordDelim) {
    StringBuilder __sb = new StringBuilder();
    char fieldDelim = delimiters.getFieldsTerminatedBy();
    __sb.append(FieldFormatter.escapeAndEnclose(emp_no==null?"null":"" + emp_no, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(dept_no==null?"null":dept_no, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(from_date==null?"null":"" + from_date, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(to_date==null?"null":"" + to_date, delimiters));
    if (useRecordDelim) {
      __sb.append(delimiters.getLinesTerminatedBy());
    }
    return __sb.toString();
  }
  public void toString0(DelimiterSet delimiters, StringBuilder __sb, char fieldDelim) {
    __sb.append(FieldFormatter.escapeAndEnclose(emp_no==null?"null":"" + emp_no, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(dept_no==null?"null":dept_no, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(from_date==null?"null":"" + from_date, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(to_date==null?"null":"" + to_date, delimiters));
  }
  private static final DelimiterSet __inputDelimiters = new DelimiterSet((char) 44, (char) 10, (char) 0, (char) 0, false);
  private RecordParser __parser;
  public void parse(Text __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharSequence __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(byte [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(char [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(ByteBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  private void __loadFromFields(List<String> fields) {
    Iterator<String> __it = fields.listIterator();
    String __cur_str = null;
    try {
    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.emp_no = null; } else {
      this.emp_no = Integer.valueOf(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null")) { this.dept_no = null; } else {
      this.dept_no = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.from_date = null; } else {
      this.from_date = java.sql.Date.valueOf(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.to_date = null; } else {
      this.to_date = java.sql.Date.valueOf(__cur_str);
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  private void __loadFromFields0(Iterator<String> __it) {
    String __cur_str = null;
    try {
    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.emp_no = null; } else {
      this.emp_no = Integer.valueOf(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null")) { this.dept_no = null; } else {
      this.dept_no = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.from_date = null; } else {
      this.from_date = java.sql.Date.valueOf(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.to_date = null; } else {
      this.to_date = java.sql.Date.valueOf(__cur_str);
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  public Object clone() throws CloneNotSupportedException {
    dept_manager o = (dept_manager) super.clone();
    o.from_date = (o.from_date != null) ? (java.sql.Date) o.from_date.clone() : null;
    o.to_date = (o.to_date != null) ? (java.sql.Date) o.to_date.clone() : null;
    return o;
  }

  public void clone0(dept_manager o) throws CloneNotSupportedException {
    o.from_date = (o.from_date != null) ? (java.sql.Date) o.from_date.clone() : null;
    o.to_date = (o.to_date != null) ? (java.sql.Date) o.to_date.clone() : null;
  }

  public Map<String, Object> getFieldMap() {
    Map<String, Object> __sqoop$field_map = new TreeMap<String, Object>();
    __sqoop$field_map.put("emp_no", this.emp_no);
    __sqoop$field_map.put("dept_no", this.dept_no);
    __sqoop$field_map.put("from_date", this.from_date);
    __sqoop$field_map.put("to_date", this.to_date);
    return __sqoop$field_map;
  }

  public void getFieldMap0(Map<String, Object> __sqoop$field_map) {
    __sqoop$field_map.put("emp_no", this.emp_no);
    __sqoop$field_map.put("dept_no", this.dept_no);
    __sqoop$field_map.put("from_date", this.from_date);
    __sqoop$field_map.put("to_date", this.to_date);
  }

  public void setField(String __fieldName, Object __fieldVal) {
    if ("emp_no".equals(__fieldName)) {
      this.emp_no = (Integer) __fieldVal;
    }
    else    if ("dept_no".equals(__fieldName)) {
      this.dept_no = (String) __fieldVal;
    }
    else    if ("from_date".equals(__fieldName)) {
      this.from_date = (java.sql.Date) __fieldVal;
    }
    else    if ("to_date".equals(__fieldName)) {
      this.to_date = (java.sql.Date) __fieldVal;
    }
    else {
      throw new RuntimeException("No such field: " + __fieldName);
    }
  }
  public boolean setField0(String __fieldName, Object __fieldVal) {
    if ("emp_no".equals(__fieldName)) {
      this.emp_no = (Integer) __fieldVal;
      return true;
    }
    else    if ("dept_no".equals(__fieldName)) {
      this.dept_no = (String) __fieldVal;
      return true;
    }
    else    if ("from_date".equals(__fieldName)) {
      this.from_date = (java.sql.Date) __fieldVal;
      return true;
    }
    else    if ("to_date".equals(__fieldName)) {
      this.to_date = (java.sql.Date) __fieldVal;
      return true;
    }
    else {
      return false;    }
  }
}
