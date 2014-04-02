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

    search(request)
    json_data = open('static/data.json').read()   
    jsonData = json.dumps(json_data)
    # Return a rendered response to send to the client.
    # We make use of the shortcut function to make our lives easier.
    # Note that the first parameter is the template we wish to use.
    return render_to_response('flickrMap/index.html', context_dict, context)
    #return render_to_response('flickrMap/index.html',{'tags':json_data})

def search(request):
    if 'tag' in request.GET:
	tag = request.GET['tag']
    else:
	tag = "No valid tag"

    print tag
    readData(request, tag)

def readData(request, text):
    with open("static/data.json") as json_file:
        json_data = json.load(json_file)

    num = 0
    locations = []

    for a in json_data:
        string = a['Tag'].encode('ascii','ignore')
        if text in string:
            num += 1
            locations.append(a['location'])

    print num
    jsonLocations = json.dumps(locations)
    print jsonLocations

    return HttpResponse(jsonLocations, mimetype='application/json')
