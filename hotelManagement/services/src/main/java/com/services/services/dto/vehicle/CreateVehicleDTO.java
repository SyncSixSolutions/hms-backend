package com.services.services.dto.vehicle;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class CreateVehicleDTO {
    private VehicleDTO vehicle;
    private VehicleAvailabilityDTO availability;
    private List<VehicleImagesDTO> images;
    private VehicleOwnersDTO owner;

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    public VehicleAvailabilityDTO getAvailability() {
        return availability;
    }

    public void setAvailability(VehicleAvailabilityDTO availability) {
        this.availability = availability;
    }

    public List<VehicleImagesDTO> getImages() {
        return images;
    }

    public void setImages(List<VehicleImagesDTO> images) {
        this.images = images;
    }

    public VehicleOwnersDTO getOwner() {
        return owner;
    }

    public void setOwner(VehicleOwnersDTO owner) {
        this.owner = owner;
    }
}
