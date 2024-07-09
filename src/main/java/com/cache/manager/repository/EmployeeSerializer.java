package com.cache.manager.repository;

/*
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import com.cache.manager.entity.Employee;

import java.io.IOException;

public class EmployeeSerializer implements StreamSerializer<Employee> {

    @Override
    public int getTypeId() {
        return 1;
    }

    @Override
    public void write(ObjectDataOutput out, Employee employee) throws IOException {
        out.writeInt(employee.getId());
        out.writeInt(employee.getPersonId());
        out.writeUTF(employee.getCompany());
    }

    @Override
    public Employee read(ObjectDataInput in) throws IOException {
        Employee e = new Employee();
        e.setId(in.readInt());
        e.setPersonId(in.readInt());
        e.setCompany(in.readUTF());
        return e;
    }

    @Override
    public void destroy() {
    }

}*/


import com.cache.manager.entity.Employee;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;
import java.util.UUID;

public class EmployeeSerializer implements StreamSerializer<Employee> {

    @Override
    public int getTypeId() {
        return 1;
    }

    @Override
    public void write(ObjectDataOutput out, Employee employee) throws IOException {
        UUID id = employee.getId();
        out.writeLong(id.getMostSignificantBits());
        out.writeLong(id.getLeastSignificantBits());
        out.writeInt(employee.getPersonId());
        out.writeUTF(employee.getCompany());
    }

    @Override
    public Employee read(ObjectDataInput in) throws IOException {
        Employee e = new Employee();
        long mostSigBits = in.readLong();
        long leastSigBits = in.readLong();
        e.setId(new UUID(mostSigBits, leastSigBits));
        e.setPersonId(in.readInt());
        e.setCompany(in.readUTF());
        return e;
    }

    @Override
    public void destroy() {
    }

}
