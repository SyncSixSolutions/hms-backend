package com.services.services.dto.vehicle;

import java.util.List;

public class VehicleResponseDTO {
    private VehicleDTO vehicle;
    private VehicleAvailabilityDTO availability;
    private List<VehicleImagesDTO> images;
    private VehicleOwnersDTO owner;

    public VehicleOwnersDTO getOwner() {
        return owner;
    }

    public void setOwner(VehicleOwnersDTO owner) {
        this.owner = owner;
    }

    public List<VehicleImagesDTO> getImages() {
        return images;
    }

    public void setImages(List<VehicleImagesDTO> images) {
        this.images = images;
    }

    public VehicleAvailabilityDTO getAvailability() {
        return availability;
    }

    public void setAvailability(VehicleAvailabilityDTO availability) {
        this.availability = availability;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }
}
