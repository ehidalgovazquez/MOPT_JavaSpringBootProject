package com.example.softlearning.core.entity.order.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.example.softlearning.core.entity.sharedkernel.domainservices.validations.Check;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.GeneralDateTimeException;
import com.example.softlearning.core.entity.sharedkernel.model.operations.Operation;
import com.example.softlearning.core.entity.sharedkernel.model.physicals.Storable;
import com.example.softlearning.core.entity.sharedkernel.model.physicals.model.PhysicalData;


public class Order extends Operation implements Storable {
    protected int idClient;
    protected String receiverAddress, receiverPerson, address, name;
    protected LocalDateTime paymentDate, deliveryDate;
    protected Set<String> phoneContact; // No admite duplicados
    protected ArrayList<OrderDetail> shopCart;
    protected PhysicalData packageInfo;
    protected OrderStatus status;

    protected Order() {
        this.phoneContact = new HashSet<>();
        this.shopCart = new ArrayList<>();
        this.status = OrderStatus.CREATED;
    }
    
    public static Order getInstance(String ref, int idClient, String startDate, String description, String address, String name, String phone) throws BuildException {
        Order o = new Order();
        String errorMessage = o.OrderDataValidation(ref, idClient, startDate, description, address, name, phone);
        if(!errorMessage.isEmpty()){
            throw new BuildException (errorMessage);
        }
        return o;
    }

    protected String OrderDataValidation(String ref, int idClient, String startDate, String description, String address, String name, String phone) {
        String errorMessage = OperationDataValidation(ref, description, startDate);

        try {
            setIdClient(idClient);
        } catch (BuildException ex) {
            errorMessage += ex.getMessage();
        }

        try {
            setAddress(address);
        } catch (BuildException ex) {
            errorMessage += ex.getMessage();
        }

        try {
            setAddress(address);
        } catch (BuildException ex) {
            errorMessage += ex.getMessage();
        }

        try {
            setName(name);
        } catch (BuildException ex) {
            errorMessage += ex.getMessage();
        }
        
        return errorMessage;
    }

    public static Order getInstance(String ref, int idClient, String startDate, String description, String address, String name, String phone, String shopcartDetails, String paymentDate, String packageInfo, String deliveryDate, String finishDate) throws BuildException {
        Order o = new Order();
        String errorMessage = o.OrderDataValidation(ref, idClient, startDate, description, address, name, phone, shopcartDetails, paymentDate, packageInfo, deliveryDate, finishDate);
        if(!errorMessage.isEmpty()){
            throw new BuildException (errorMessage);
        }
        return o;
    }

    protected String OrderDataValidation(String ref, int idClient, String startDate, String description, String address, String name, String phone, String shopcartDetails, String paymentDate, String packageInfo, String deliveryDate, String finishDate) {
        String errorMessage = OrderDataValidation(ref, idClient, startDate, description, address, name, phone);
        if(shopcartDetails != null){
            try {
                setShopcartDetails(shopcartDetails);
            } catch (BuildException ex) {
                errorMessage += ex.getMessage();
            }
        }

        if(paymentDate != null){
            try {
                setPaymentDate(paymentDate);
            } catch (GeneralDateTimeException ex) {
                errorMessage += "Bad payment date: " + ex.getMessage() + ";";
            }
        }

        if(packageInfo != null){
            try {
                setPackageInfo(packageInfo);
            } catch (BuildException ex) {
                errorMessage += ex.getMessage();
            }
        }

        if(deliveryDate != null){
            try {
                setDeliveryDate(deliveryDate);
            } catch (GeneralDateTimeException ex) {
                errorMessage += ex.getMessage();
            }
        }

        if(finishDate != null){
            try {
                setFinishDate(finishDate);
            } catch (GeneralDateTimeException ex) {
                errorMessage += ex.getMessage();
            }
        }

        return errorMessage;
    }

    public String getStatus() {
        return status.toString();
    }

    public void orderCancelled() {
        this.status = OrderStatus.CANCELLED;
    }
   
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) throws BuildException {
        if(this.status != OrderStatus.CREATED){
            throw new BuildException("Order Status differs to created");
        }
        
        if(!Check.minValue(idClient, 0)){
            throw new BuildException("Bad idClient");
        }

        this.idClient = idClient;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws BuildException {
        if(this.status != OrderStatus.CREATED){
            throw new BuildException("Order Status differs to created");
        }

        if(!Check.minStringChars(address, 4)) {
            throw new BuildException("Bad Address");
        }

        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws BuildException {
        if(this.status != OrderStatus.CREATED){
            throw new BuildException("Order Status differs to created");
        }

        if(!Check.minStringChars(name, 3)){
            throw new BuildException("Bad Name");
        }

        this.name = name;
    }

    public String getPhoneContact() {
        String result = "";

        for (String phone : phoneContact) {
            result += phone + "; ";
        }

        if (!result.isEmpty()) {
            result = result.substring(0, result.length() - 2);
        }

        return result;
    }
    
    public void setPhoneContact(String phoneContact) throws BuildException {
        if (this.status != OrderStatus.CREATED) {
            throw new BuildException("Order Status differs to created");
        }

        String[] phones = phoneContact.split(";");

        for (String phone : phones) {
            phone = phone.trim();

            if (!Check.minStringChars(phone, 11)) {
                throw new BuildException("Bad PhoneContact: " + phone);
            }

            this.phoneContact.add(phone);
        }
    }

    public String getShopcartDetails() {
        String result = "";

        for (OrderDetail detail : shopCart) {
            result += detail.getRef() + ", "
                + detail.getAmount() + ", "
                + detail.getPrice() + ", "
                + detail.getDiscount() + "; ";
        }

        if (!result.isEmpty()) {
            result = result.substring(0, result.length() - 2);
        }

        return result;
    }

    public void setShopcartDetails(String shopcartDetails) throws BuildException {      
        if(this.status != OrderStatus.CREATED){
            throw new BuildException("Order Status differs to Created");
        }
        shopCart.clear();
        String[] items = shopcartDetails.split(";");
        int lineNumber = 0;
        for (String item : items) {
            lineNumber++;
            item = item.trim();
            if (!item.isEmpty()) {
                String[] parts = item.split(",");
                if (parts.length != 4) {
                    throw new BuildException("Error en objeto " + lineNumber + ": se esperaban 4 campos, recibido -> " + item);
                }

                OrderDetail detail = new OrderDetail();
                String id = parts[0].trim();
                int amount = Integer.parseInt(parts[1].trim());
                double price = Double.parseDouble(parts[2].trim());
                double discount = Double.parseDouble(parts[3].trim());
                String validation = detail.OrderDetailDataValidation(id, amount, price, discount);

                if (!validation.isEmpty()) {
                    throw new BuildException("Error en objeto " + lineNumber + ": " + validation);
                }

                shopCart.add(detail);
            }
        }
    }

    public void createDetail(String ref, int amount, double price, double discount) throws BuildException {
        if(this.status != OrderStatus.CREATED){
            throw new BuildException("Order Status differs to Created");
        }

        OrderDetail detail = new OrderDetail();
        String errorMessage = detail.OrderDetailDataValidation(ref, amount, price, discount);

        if (!errorMessage.isEmpty()) {
            throw new BuildException(errorMessage);
        }

        this.shopCart.add(detail);
    }

    public OrderDetail readDetail(String ref)  {
        for (OrderDetail od : shopCart){
            if(od.getRef().equalsIgnoreCase(ref.trim())){
                return od;
            }
        }
        return null;
    }

    public void updateDetail(String ref, int amount) throws BuildException {
        if(this.status != OrderStatus.CREATED){
            throw new BuildException("Order Status differs to Created");
        }

        OrderDetail detail = readDetail(ref);
        if(detail == null){
            throw new BuildException("Not found detail by ref (" + ref + ")");
        }

        if(detail.setAmount(amount) != 0){
            throw new BuildException("Bad amount");
        }
    }
    
    public void updateDetail(int pos, int amount) throws BuildException {
        if(this.status != OrderStatus.CREATED){
            throw new BuildException("Order Status differs to Created");
        }

        if (pos < 0 || pos >= this.shopCart.size()) {
            throw new BuildException("Not found detail by pos (" + pos + ")");
        }

        OrderDetail detail = this.shopCart.get(pos);

        if (detail.setAmount(amount) != 0) {
            throw new BuildException("Bad amount");
        }
    }

    public void deleteDetail(String ref) throws BuildException {
        if(this.status != OrderStatus.CREATED){
            throw new BuildException("Order Status differs to Created");
        }
        
        if (ref == null || ref.trim().isEmpty()) {
            throw new BuildException("Bad ref");
        }

        OrderDetail detailToRemove = null;

        for (OrderDetail detail : shopCart) {
            if (detail.getRef().equalsIgnoreCase(ref.trim())) {
                detailToRemove = detail;
                break;
            }
        }

        if (detailToRemove == null) {
            throw new BuildException("Not found detail by ref (" + ref + ")");
        }

        this.shopCart.remove(detailToRemove);
    }

    public void deleteDetail(int pos) throws BuildException {
        if(this.status != OrderStatus.CREATED){
            throw new BuildException("Order Status differs to Created");
        }
        
        if (pos < 0 || pos >= this.shopCart.size()) {
            throw new BuildException("Not found detail by pos (" + pos + ")");
        }

        OrderDetail detailToRemove = this.shopCart.get(pos);

        if (detailToRemove == null) {
            throw new BuildException("Not found detail by pos (" + pos + ")");
        }

        this.shopCart.remove(detailToRemove);
    }

    public String getPaymentDate() {
        if(this.paymentDate == null){
            return null;
        }

        return this.paymentDate.format(formatter);
    }

    public void setPaymentDate(String paymentDate) throws GeneralDateTimeException {
        if(this.status != OrderStatus.CREATED){
            throw new GeneralDateTimeException("Order Status differs to created");
        }

        if(paymentDate == null){
            throw new GeneralDateTimeException("HEMOS RECIBIDO UN NULL EN LUGAR DE UNA FECHA");
        }

        try{
            this.paymentDate = LocalDateTime.parse(paymentDate,formatter);
        } catch (GeneralDateTimeException e) {
            throw e;
        }

        this.status = OrderStatus.CONFIRMED;
    }

    public void setPackageInfo(String packageinfo) throws BuildException {
        if(this.status != OrderStatus.CONFIRMED){
            throw new BuildException("Status differs to Confirmed");
        }

        this.packageInfo = PhysicalData.getInstance(packageinfo);

        this.status = OrderStatus.FORTHCOMMING;
    }

    public String getDeliveryDate() {
        if(this.deliveryDate == null){
            return null;
        }
        return this.deliveryDate.format(formatter);
    }

    public void setDeliveryDate(String deliveryDate) throws GeneralDateTimeException {
        if(this.status != OrderStatus.FORTHCOMMING){
            throw new GeneralDateTimeException("Order status differs to confirmed");
        }

        if(deliveryDate == null){
            throw new GeneralDateTimeException("HEMOS RECIBIDO UN NULL EN LUGAR DE UNA FECHA");
        }

        try{
            this.deliveryDate = LocalDateTime.parse(deliveryDate,formatter);
        } catch (GeneralDateTimeException e) {
            throw e;
        }

        this.status = OrderStatus.DELIVERED;
    }

    @Override
    public void setFinishDate(String finishDate) throws GeneralDateTimeException {
        if(this.status != OrderStatus.DELIVERED) {
            throw new GeneralDateTimeException("Order Status differs to Delivered");
        }

        super.setFinishDate(finishDate); 

        // Si peta el padre, el hijo la pasa a quien se lo ha pedido (Delegación). Por eso no se hace un try.
        this.status = OrderStatus.FINISHED;
    }
    
    @Override
    public String getPhysicalData() {
        return this.packageInfo.getPhysicalData();
    }

    @Override
    public String getDimensions() {
        return this.packageInfo.getDimensions();
    }

    @Override
    public double getWeight() {
        return this.packageInfo.getWeight();
    }

    @Override
    public double getWidth() {
        return this.packageInfo.getWidth();
    }

    @Override
    public double getHeight() {
        return this.packageInfo.getHeight();
    }

    @Override
    public double getDepth() {
        return this.packageInfo.getDepth();
    }

    @Override
    public boolean getIsFragile() {
        return this.packageInfo.getIsFragile();
    }

    @Override
    public void setDimensions(double width, double height, double depth) throws BuildException {
        try {
            this.packageInfo.setDimensions(width, height, depth);
        } catch (BuildException e) {
            throw e;
        }
    }
}