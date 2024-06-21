import { Flex, Container, VStack, Divider, Text, Box, Button, Input, IconButton } from '@chakra-ui/react';
import { SearchIcon } from '@chakra-ui/icons';

// todo break into sub Components
const Content = () => {
    return (
        <Container height="calc(100vh - 8rem)" maxW="80vw" padding="4" mt={5} mb={5}>
            <Flex direction="column" height="100%">
                <Flex flex="1" direction={{ base: 'column', md: 'row' }} padding="4" overflowY="auto">
                    {/* Cards */}
                    <VStack spacing={4} p={4} w="20rem" overflowY="auto" borderRight="1px solid #E2E8F0">
                        <Text fontSize="xl" fontWeight="bold">Bike Racks</Text>
                        <Divider />
                        <Box w="100%" p={2} borderWidth="1px" rounded="md">
                            <Text>Bike Rack</Text>
                        </Box>
                        <Box w="100%" p={2} borderWidth="1px" rounded="md">
                            <Text>Bike Rack</Text>
                        </Box>
                        <Box w="100%" p={2} borderWidth="1px" rounded="md">
                            <Text>Bike Rack</Text>
                        </Box>
                        <Box w="100%" p={2} borderWidth="1px" rounded="md">
                            <Text>Bike Rack</Text>
                        </Box>
                        <Box w="100%" p={2} borderWidth="1px" rounded="md">
                            <Text>Bike Rack</Text>
                        </Box>
                        <Box w="100%" p={2} borderWidth="1px" rounded="md">
                            <Text>Bike Rack</Text>
                        </Box>
                        <Box w="100%" p={2} borderWidth="1px" rounded="md">
                            <Text>Bike Rack</Text>
                        </Box>
                        <Box w="100%" p={2} borderWidth="1px" rounded="md">
                            <Text>Bike Rack</Text>
                        </Box>
                        <Box w="100%" p={2} borderWidth="1px" rounded="md">
                            <Text>Bike Rack</Text>
                        </Box>
                        <Box w="100%" p={2} borderWidth="1px" rounded="md">
                            <Text>Bike Rack</Text>
                        </Box>
                    </VStack>

                    {/* Google Maps */}
                    <Flex direction="column" flex="1">
                        {/* Search Bar */}
                        <Flex alignItems="center" justifyContent="center" m={3}>
                            <Input placeholder="Search..." variant="filled" size="md" mr={3} />
                            <IconButton
                                colorScheme='blue'
                                aria-label='Search database'
                                icon={<SearchIcon />}
                            />
                        </Flex>

                        {/* Map */}
                        <Box flex="1" bg="green" mb={4}>
                            Google Maps here
                        </Box>

                        <Flex alignItems="center" justifyContent="center">
                            <Flex flex="1" justifyContent="center" alignItems="center">
                                <Button>Find Closest Rack</Button>
                            </Flex>
                            <Flex flex="1" justifyContent="center" alignItems="center">
                                <Button>Report New Rack</Button>
                            </Flex>
                        </Flex>
                    </Flex>
                </Flex>
            </Flex>
        </Container>
    );
};

export default Content;