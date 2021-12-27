package org.example;

import org.example.services.Service;
import org.example.services.impl.ServiceImpl;


public class App 
{

    public static void main( String [] args )
    {
        Service service = new ServiceImpl();
        service.startProcess();
    }
}
