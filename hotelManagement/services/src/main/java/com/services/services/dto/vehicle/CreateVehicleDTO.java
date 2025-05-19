package com.services.services.dto.vehicle;

import com.services.services.model.vehicel.VehicleImages;
import com.services.services.model.vehicel.VehicleOwners;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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

    public List<VehicleImages> getImages() {
        return images;
    }

    public void setImages(List<VehicleImagesDTO> images) {
        this.images = images;
    }

    public VehicleOwners getOwner() {
        return owner;
    }

    public void setOwner(VehicleOwnersDTO owner) {
        this.owner = owner;
    }
}
