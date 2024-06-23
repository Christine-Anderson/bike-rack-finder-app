import { Flex, Container, VStack, Divider, Text, Button, Input, IconButton } from "@chakra-ui/react";
import { SearchIcon } from "@chakra-ui/icons";
import BikeRackCard from "./BikeRackCard";
import BikeRackMap from "./BikeRackMap";
import ReportModal from "./ReportModal";

const mockBikeRacks = [
    { poi: {key: '123 Fake Street, City', location: { lat: 49.2827, lng: -123.1207 }}, numThefts: 5, rating: 3.5},
    { poi: {key: '123 Fake Street, City', location: { lat: 49.3043, lng: -123.1443 }}, numThefts: 5, rating: 3.5},
    { poi: {key: '123 Fake Street, City', location: { lat: 49.2713, lng: -123.1340 }}, numThefts: 5, rating: 3.5},
    { poi: {key: '123 Fake Street, City', location: { lat: 49.2606, lng: -123.2460 }}, numThefts: 5, rating: 3.5},
    { poi: {key: '123 Fake Street, City', location: { lat: 49.2886, lng: -123.1112 }}, numThefts: 5, rating: 3.5},
];

const Content = () => {
    return (
        <Container height="calc(100vh - 8rem)" maxW="80vw" padding="4" mt={5} mb={5}>
            <Flex direction="column" height="100%">
                <Flex flex="1" direction={{ base: "column", md: "row" }} padding="4" overflowY="auto">
                    <Flex direction="column">
                        <Text textAlign="center" fontSize="xl" fontWeight="bold" mb={2}>Bike Racks</Text>
                        <Divider />
                        <VStack spacing={4} p={4} w="20rem" overflowY="auto" borderRight="1px solid #E2E8F0">
                            
                            <BikeRackCard
                                address={mockBikeRacks[0].poi.key}
                                numThefts={mockBikeRacks[0].numThefts}
                                rating={mockBikeRacks[0].rating}
                            />
                            <BikeRackCard
                                address={mockBikeRacks[1].poi.key}
                                numThefts={mockBikeRacks[1].numThefts}
                                rating={mockBikeRacks[1].rating}
                            />
                            <BikeRackCard
                                address={mockBikeRacks[2].poi.key}
                                numThefts={mockBikeRacks[2].numThefts}
                                rating={mockBikeRacks[2].rating}
                            />

                        </VStack>
                    </Flex>

                    <Flex direction="column" flex="1">
                        {/* Search Bar */}
                        <Flex alignItems="center" justifyContent="center" m={3}>
                            <Input placeholder="Search..." variant="filled" size="md" mr={3} />
                            <IconButton
                                colorScheme="blue"
                                aria-label="Search database"
                                icon={<SearchIcon />}
                            />
                        </Flex>

                        <BikeRackMap mockBikeRacks={mockBikeRacks}></BikeRackMap>

                        <Flex alignItems="center" justifyContent="space-between" mt={2}>
                            <Button colorScheme="blue" left="50%" transform="translateX(-50%)">Find Closest Rack</Button>
                            <ReportModal
                                reportType={"New Rack"}
                                address={"test"}
                                buttonSize={"md"}
                                buttonRight={"0"}
                                // todo figure out how to pass in the one that was clicked
                            />
                        </Flex>
                    </Flex>
                </Flex>
            </Flex>
        </Container>
    );
};

export default Content;