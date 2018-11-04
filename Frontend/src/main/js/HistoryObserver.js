export function createHistoryObserver(pathRegexes) {
  return function onHistoryEvent(historyEvent) {
    pathRegexes.forEach(({ regex, callback }) => {
      const matches = regex.exec(historyEvent.hash)
      if (matches) {
        const [IGNORED, ...captures] = matches
        callback(captures)
      }
    })
  }
}
