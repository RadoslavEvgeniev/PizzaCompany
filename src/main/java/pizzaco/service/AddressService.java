package pizzaco.service;

import pizzaco.domain.models.service.AddressServiceModel;
import pizzaco.domain.models.view.AddressViewModel;

import java.util.List;

public interface AddressService {

    boolean addAddress(AddressServiceModel addressServiceModel);

    AddressServiceModel getAddressById(String id);

    boolean editAddress(AddressServiceModel addressServiceModel);

    List<AddressServiceModel> getUserAddressesOrderedByName(String email);
}
