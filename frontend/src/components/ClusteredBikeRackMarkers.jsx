import React, { useCallback, useEffect, useMemo, useState } from 'react';
import { MarkerClusterer } from '@googlemaps/markerclusterer';
import { InfoWindow, useMap } from '@vis.gl/react-google-maps';
import BikeRackMarker from './BikeRackMarker';


const ClusteredBikeRackMarkers = ({ bikeRacks }) => {
    const [markers, setMarkers] = useState({});
    const [selectedBikeRackId, setSelectedBikeRackId] = useState(null);

    const selectedBikeRack = useMemo(
        () =>
            bikeRacks && selectedBikeRackId
            ? bikeRacks.find((rack) => rack.poi.rackId === selectedBikeRackId)
            : null,
        [bikeRacks, selectedBikeRackId]
    );

    const map = useMap();
    const clusterer = useMemo(() => {
        if (!map) return null;

        return new MarkerClusterer({ map });
    }, [map]);

    useEffect(() => {
        if (!clusterer) return;

        clusterer.clearMarkers();
        clusterer.addMarkers(Object.values(markers));
    }, [clusterer, markers]);

    const setMarkerRef = useCallback((marker, key) => {
        setMarkers(markers => {
            if ((marker && markers[key]) || (!marker && !markers[key]))
                return markers;
        
            if (marker) {
                return { ...markers, [key]: marker };
            } else {
                // eslint-disable-next-line no-unused-vars
                const { [key]: _, ...newMarkers } = markers;
                return newMarkers;
            }
        });
    }, []);

    const handleInfoWindowClose = useCallback(() => {
        setSelectedBikeRackId(null);
    }, []);

    const handleMarkerClick = useCallback(
        (bikeRack) => {
        setSelectedBikeRackId(bikeRack.poi.rackId);
        },
        []
    );

    return (
        <>
            {bikeRacks?.map(rack => (
                <BikeRackMarker
                    key={rack.poi.rackId}
                    rack={rack}
                    onClick={handleMarkerClick}
                    setMarkerRef={setMarkerRef}
                />
            ))}

            {selectedBikeRackId && (
                <InfoWindow
                    anchor={markers[selectedBikeRackId]}
                    onCloseClick={handleInfoWindowClose}>
                    <h1>{selectedBikeRack && selectedBikeRack.address}</h1>
                </InfoWindow>
            )}
        </>
    );
};

export default ClusteredBikeRackMarkers;
