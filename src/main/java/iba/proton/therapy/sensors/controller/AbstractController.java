package iba.proton.therapy.sensors.controller;


import iba.proton.therapy.sensors.model.base.ResultObject;

public abstract class AbstractController {
    protected String appName;

    public AbstractController(final String appName) {
        this.appName = appName;
    }

    public <C> ResultObject<C> mapToResultObject(C channelObject) {
        return new ResultObject<>(appName, true, channelObject);
    }

}