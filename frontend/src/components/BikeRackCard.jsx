import React, { useRef, useEffect } from 'react';
import { Card, CardHeader, CardBody, CardFooter, Text, Heading, Flex } from "@chakra-ui/react";

import StarRating from "./StarRating";
import ReportModal from "./ReportModal";
import RatingModal from "./RatingModal";

const BikeRackCard = ({rackId, address, numThefts, rating, isSelected}) => {
    const cardRef = useRef(null);

    useEffect(() => {
        if (isSelected && cardRef.current) {
            cardRef.current.scrollIntoView({ behavior: 'smooth', block: 'start' });
        }
    }, [isSelected]);

    return (
        <Card
            align="center"
            width="100%"
            size="sm"
            ref={cardRef}
            outline={isSelected ? '2px solid blue' : 'none'}
        >
            <CardHeader>
                <Heading size="sm"> {address} </Heading>
            </CardHeader>
            <CardBody>
                <Flex direction="column" gap={4}>
                    <Flex justify="space-between" align="center" width="100%">
                        <Text size="sm" mr={10}>{`Thefts: ${numThefts}`}</Text>
                        <ReportModal
                            rackId={rackId}
                            reportType={"Theft"}
                            address={address}
                            buttonSize={"sm"}
                            buttonRight={"auto"}
                        />
                    </Flex>
                    <Flex justify="space-between" align="center" width="100%">
                        <StarRating rating={rating}/>
                        <RatingModal rackId={rackId}/>
                    </Flex>
                </Flex>
            </CardBody>
            <CardFooter>
                <ReportModal
                    rackId={rackId}
                    reportType={"Removal"}
                    address={address}
                    buttonSize={"sm"}
                    buttonRight={"auto"}
                />
            </CardFooter>
        </Card>
    );
};

export default BikeRackCard;