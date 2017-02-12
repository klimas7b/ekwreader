package pl.klimas7.ekwreader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;

public class EKWHeader {
    private Boolean exists = false;
    private String type;
    private String department;
    private LocalDate writingDate;
    private LocalDate closingDate;
    private String position;
    private List<String> owners;

    public EKWHeader() {
        this.owners = new ArrayList<>();
    }

    public EKWHeader type(String type) {
        this.type = type;
        return this;
    }

    public EKWHeader department(String department) {
        this.department = department;
        return this;
    }

    public EKWHeader writingDate(LocalDate writingDate) {
        this.writingDate = writingDate;
        return this;
    }

    public EKWHeader closingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
        return this;
    }

    public EKWHeader position(String position) {
        this.position = position;
        return this;
    }

    public EKWHeader owners(String[] owners) {
        for (String owner : owners) {
            if (StringUtil.isNotBlank(owner)) {
                this.owners.add(owner);
            }
        }
        return this;
    }

    public String getOwnersAsText() {
        if (!exists) {
            return "----";
        }
        return StringUtils.join(owners, ',');
    }

    public void setExists() {
        this.exists = true;
    }

    public String getType() {
        return type;
    }

    public String getDepartment() {
        return department;
    }

    public LocalDate getWritingDate() {
        return writingDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public String getPosition() {
        return position == null ? "----" : position;
    }

    public List<String> getOwners() {
        return owners;
    }
}
