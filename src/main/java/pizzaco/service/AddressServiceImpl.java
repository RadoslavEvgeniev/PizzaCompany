package pizzaco.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pizzaco.domain.entities.Address;
import pizzaco.domain.models.service.AddressServiceModel;
import pizzaco.errors.IdNotFoundException;
import pizzaco.repository.AddressRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean addAddress(AddressServiceModel addressServiceModel) {
        this.addressRepository.save(this.modelMapper.map(addressServiceModel, Address.class));

        return true;
    }

    @Override
    public AddressServiceModel getAddressById(String id) {
        Address addressEntity = this.addressRepository.findById(id).orElse(null);

        if (addressEntity == null) {
            throw new IdNotFoundException("Wrong or non-existent id.");
        }

        return this.modelMapper.map(addressEntity, AddressServiceModel.class);
    }

    @Override
    public boolean editAddress(AddressServiceModel addressServiceModel) {
        Address addressEntity = this.addressRepository.findById(addressServiceModel.getId()).orElse(null);

        if (addressEntity == null) {
            throw new IdNotFoundException("Wrong or non-existent id.");
        }

        addressEntity = this.modelMapper.map(addressServiceModel, Address.class);

        this.addressRepository.save(addressEntity);

        return true;
    }

    @Override
    public List<AddressServiceModel> getUserAddressesOrderedByName(String email) {
        return this.addressRepository.findAllUserAddressesOrderedByName(email)
                .stream()
                .map(address -> this.modelMapper.map(address, AddressServiceModel.class))
                .collect(Collectors.toList());
    }
}
