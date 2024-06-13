import os
from dotenv import load_dotenv
import json
import googlemaps

load_dotenv()
api_key = os.getenv('API_KEY')

data_file = open("bike-racks.json")
data = json.load(data_file)

sql_script = open("V5__add_city_of_vancouver_bike_rack_data.sql", "w")
sql_script.write("INSERT INTO bike_rack (rack_id, latitude, longitude) VALUES\n")

gmaps = googlemaps.Client(key=api_key)

for i in range(len(data)):
    bike_rack_address = data[i]["street_number"] + ", " + data[i]["street_name"] + ", Vancouver, BC"
    geocode_result = gmaps.geocode(bike_rack_address)
    latitude = geocode_result[0]["geometry"]["location"]["lat"]
    longitude = geocode_result[0]["geometry"]["location"]["lng"]
    
    sql_script.write("\t(UUID(), " + str(latitude) + ", " + str(longitude) + ")")
    
    if i < len(data) - 1:
        sql_script.write(",\n")
    else:
        sql_script.write(";")
    
data_file.close()