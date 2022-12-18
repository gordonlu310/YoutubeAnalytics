function createReport(startTimestamp, endTimestamp) {
  const channels = YouTube.Channels.list('id,contentDetails', {
    mine: true
  });
  const channelId = channels.items[0].id;
  const startTime = new Date(startTimestamp);
  const endTime = new Date(endTimestamp);
  const metrics = [
    'views',
    'estimatedMinutesWatched',
    'averageViewDuration',
    'averageViewPercentage',
    'likes',
    'dislikes',
    'subscribersGained',
    'subscribersLost',
    'comments'
  ];
  const result = YouTubeAnalytics.Reports.query({
    ids: 'channel==' + channelId,
    startDate: formatDateString(startTime),
    endDate: formatDateString(endTime),
    metrics: metrics.join(','),
    dimensions: 'day',
    sort: 'day'
  });
  if (!result.rows) {
    return {};
  }
  const headers = result.columnHeaders.map((columnHeader)=> {
    return formatColumnName(columnHeader.name);
  });
  return result.rows;
}

function createVideoReport(startTimestamp, endTimestamp, videoIds) {
  const channels = YouTube.Channels.list('id,contentDetails', {
    mine: true
  });
  const channelId = channels.items[0].id;
  const startTime = new Date(startTimestamp);
  const endTime = new Date(endTimestamp);
  const metrics = [
    'views',
    'estimatedMinutesWatched',
    'averageViewDuration',
    'averageViewPercentage',
    'likes',
    'dislikes',
    'subscribersGained',
    'subscribersLost',
    'comments'
  ];
  const result = YouTubeAnalytics.Reports.query({
    ids: 'channel==' + channelId,
    startDate: formatDateString(startTime),
    endDate: formatDateString(endTime),
    metrics: metrics.join(','),
    dimensions: 'day',
    sort: 'day',
    filters: 'video==' + videoIds
  });
  if (!result.rows) {
    return {};
  }
  const headers = result.columnHeaders.map((columnHeader)=> {
    return formatColumnName(columnHeader.name);
  });
  return result.rows;
}

function test(){
  // this is a test function where I test the program, and is NOT necessary in the actual script, although you
  // can keep it for your own reference
  console.log(createVideoReport("January 1, 2022", "March 1, 2022", "S1g_CCm3XBg").toString());
}

function formatDateString(date) {
  return Utilities.formatDate(date, Session.getScriptTimeZone(), 'yyyy-MM-dd');
}

function formatColumnName(columnName) {
  let name = columnName.replace(/([a-z])([A-Z])/g, '$1 $2');
  name = name.slice(0, 1).toUpperCase() + name.slice(1);
  return name;
}

function doGet(e){
  try {
    if (e.parameter.action == "channelReport") {
      return ContentService.createTextOutput(createReport(e.parameter.startTime, e.parameter.endTime).toString());
    } else if (e.parameter.action == "videoReport") {
      return ContentService.createTextOutput(createVideoReport(e.parameter.startTime, e.parameter.endTime, e.parameter.videoIds).toString());
    }
  } catch (error) {
    return ContentService.createTextOutput("%" + error.toString());
  }
}
