import { Flex, Container, VStack, Divider, Text, Button, Input, IconButton } from "@chakra-ui/react";
import { SearchIcon } from "@chakra-ui/icons";
import BikeRackCard from "./BikeRackCard";
import ReportModal from "./ReportModal";

const mockBikeRack = {
    address: "123 Fake Street, City",
    numThefts: 5,
    rating: 3.5
}; 

const Content = () => {
    return (
        <Container height="calc(100vh - 8rem)" maxW="80vw" padding="4" mt={5} mb={5}>
            <Flex direction="column" height="100%">
                <Flex flex="1" direction={{ base: "column", md: "row" }} padding="4" overflowY="auto">
                    {/* Cards */}
                    <Flex direction="column">
                        <Text textAlign="center" fontSize="xl" fontWeight="bold" mb={2}>Bike Racks</Text>
                        <Divider />
                        <VStack spacing={4} p={4} w="20rem" overflowY="auto" borderRight="1px solid #E2E8F0">
                            
                            <BikeRackCard
                                address={mockBikeRack.address}
                                numThefts={mockBikeRack.numThefts}
                                rating={mockBikeRack.rating}
                            />
                            <BikeRackCard
                                address={mockBikeRack.address}
                                numThefts={mockBikeRack.numThefts}
                                rating={mockBikeRack.rating}
                            />
                            <BikeRackCard
                                address={mockBikeRack.address}
                                numThefts={mockBikeRack.numThefts}
                                rating={mockBikeRack.rating}
                            />
                            <BikeRackCard
                                address={mockBikeRack.address}
                                numThefts={mockBikeRack.numThefts}
                                rating={mockBikeRack.rating}
                            />

                        </VStack>
                    </Flex>

                    {/* Google Maps */}
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

                        {/* Map */}
                        <Flex flex="1" bg="green" mb={4}>
                            Google Maps here
                        </Flex>

                        <Flex alignItems="center" justifyContent="space-between">
                            <Button colorScheme="blue" left="50%" transform="translateX(-50%)">Find Closest Rack</Button>
                            <ReportModal
                                reportType={"New Rack"}
                                address={mockBikeRack.address}
                                buttonSize={"md"}
                                buttonRight={"0"}
                            />
                        </Flex>
                    </Flex>
                </Flex>
            </Flex>
        </Container>
    );
};

export default Content;