package br.com.segment.leosmusicstoreapi.controllers;

import br.com.segment.leosmusicstoreapi.components.SegmentHelper;
import br.com.segment.leosmusicstoreapi.dtos.GuestOrderDrumKitPostDto;
import br.com.segment.leosmusicstoreapi.dtos.GuestOrderPostDto;
import br.com.segment.leosmusicstoreapi.helpers.MapsHelper;
import br.com.segment.leosmusicstoreapi.models.DrumKit;
import br.com.segment.leosmusicstoreapi.models.GuestOrder;
import br.com.segment.leosmusicstoreapi.models.GuestOrderDrumKit;
import br.com.segment.leosmusicstoreapi.repositories.DrumKitRepository;
import br.com.segment.leosmusicstoreapi.repositories.GuestOrderRepository;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/guest-orders")
@CrossOrigin
public class GuestOrdersController {

    @Autowired
    private DrumKitRepository drumKitRepository;

    @Autowired
    private GuestOrderRepository guestOrderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SegmentHelper segmentHelper;

    @GetMapping("")
    public ResponseEntity<?> getAllGuestOrders() {
        return new ResponseEntity<>(guestOrderRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneOrder(@PathVariable @NotNull Long id) {
        Optional<GuestOrder> guestOrder = guestOrderRepository.findById(id);
        if (guestOrder.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(guestOrder, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addOrder(@RequestBody GuestOrderPostDto guestOrderPostDto) throws InvocationTargetException, IllegalAccessException {
        List<GuestOrderDrumKitPostDto> guestOrderDrumKits = guestOrderPostDto.getGuestOrderDrumKits();
        if (guestOrderDrumKits == null || guestOrderDrumKits.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        GuestOrder guestOrder = modelMapper.map(guestOrderPostDto, GuestOrder.class);
        guestOrder.setGuestOrderDrumKits(new HashSet<>());

        for (GuestOrderDrumKitPostDto guestOrderDrumKitPostDto: guestOrderPostDto.getGuestOrderDrumKits()) {
            Optional<DrumKit> attemptedDrumKit = drumKitRepository.findById(guestOrderDrumKitPostDto.getDrumKitId());
            if (attemptedDrumKit.isEmpty()) {
                continue;
            }

            DrumKit drumKit = attemptedDrumKit.get();
            GuestOrderDrumKit guestOrderDrumKit = new GuestOrderDrumKit(
                    null,
                    guestOrderDrumKitPostDto.getQuantity(),
                    drumKit.getPrice(),
                    null,
                    drumKit
            );
            guestOrder.getGuestOrderDrumKits().add(guestOrderDrumKit);
        }

        guestOrderRepository.save(guestOrder);

        segmentHelper.trackEvent("Guest Order Placed",
                "guest-order-" + guestOrder.getId(),
                MapsHelper.objectToMap(guestOrder, 0));

        return new ResponseEntity<>(guestOrder, HttpStatus.ACCEPTED);
    }
}
