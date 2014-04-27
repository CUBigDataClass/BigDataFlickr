from django.template import RequestContext
from django.shortcuts import render_to_response
from django.http import HttpResponse
import json

def index(request):
    # Request the context of the request.
    # The context contains information such as the client's machine details, for example.
    context = RequestContext(request)

    # Construct a dictionary to pass to the template engine as its context.
    # Note the key boldmessage is the same as {{ boldmessage }} in the template!
    context_dict = {'boldmessage': "I am bold font from the context"}

    data = search(request)
    json_data = open('static/data.json').read()   
    jsonData = json.dumps(json_data)

    print data
    # Return a rendered response to send to the client.
    # We make use of the shortcut function to make our lives easier.
    # Note that the first parameter is the template we wish to use.
    #return render_to_response('flickrMap/index.html', context_dict, context)
    return render_to_response('flickrMap/index.html', {'tags': data}, context)

def search(request):
    if 'tag' in request.GET:
	tag = request.GET['tag']
    else:
	tag = "No valid tag"

    print tag
    return readData(request, tag)

def readData(request, text):
    with open("static/data.json") as json_file:
        json_data = json.load(json_file)

    num = 0
    resultDict = {}
    locations = []

    for a in json_data:
        string = a["_id"]['tag'].encode('ascii','ignore')
        if text == string:
            num += 1
            if(a["_id"]['location'] in locations):
                continue
            else:
                locations.append(a["_id"]['location'])
                resultDict[a["_id"]['location']] = a['value']

    #print resultDict
    jsonDict = json.dumps(resultDict)
    print jsonDict
    jsonLocations = json.dumps(locations)
    #print jsonLocations
    print num

    #return HttpResponse(jsonLocations, content_type='application/json')
    #return HttpResponse(jsonLocations)
    return jsonDict